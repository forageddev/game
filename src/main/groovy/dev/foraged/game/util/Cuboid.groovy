package dev.foraged.game.util

import com.google.common.base.Preconditions
import com.google.gson.JsonObject
import org.bukkit.*
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.util.Vector

class Cuboid implements Cloneable {

    private static int CHUNK_SIZE = 16
    protected String worldName
    protected Location locationOne, locationTwo
    protected int x1, y1, z1, x2, y2, z2

    Cuboid(Map<String, Object> map) {
        this.worldName = (String) map.get("worldName")
        this.locationOne = null
        this.locationTwo =  null
        this.x1 = (Integer) map.get("x1")
        this.y1 = (Integer) map.get("y1")
        this.z1 = (Integer) map.get("z1")
        this.x2 = (Integer) map.get("x2")
        this.y2 = (Integer) map.get("y2")
        this.z2 = (Integer) map.get("z2")
    }

    Cuboid(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
        this(((World) Preconditions.checkNotNull((Object) world)).getName(), x1, y1, z1, x2, y2, z2)
    }

    private Cuboid(String worldName, int x1, int y1, int z1, int x2, int y2, int z2) {
        Preconditions.checkNotNull((Object) worldName, "World name cannot be null")
        this.worldName = worldName
        this.locationOne = null
        this.locationTwo =  null
        this.x1 = Math.min(x1, x2)
        this.y1 = Math.min(y1, y2)
        this.z1 = Math.min(z1, z2)
        this.x2 = Math.max(x1, x2)
        this.y2 = Math.max(y1, y2)
        this.z2 = Math.max(z1, z2)
    }

    Cuboid(Location first, Location second) {
        Preconditions.checkNotNull((Object) first, "Location 1 cannot be null")
        Preconditions.checkNotNull((Object) second, "Location 2 cannot be null")
        Preconditions.checkArgument(first.getWorld().equals(second.getWorld()), "Locations must be on the same world")
        this.worldName = first.getWorld().getName()
        this.locationOne = first
        this.locationTwo = second
        this.x1 = Math.min(first.getBlockX(), second.getBlockX())
        this.y1 = Math.min(first.getBlockY(), second.getBlockY())
        this.z1 = Math.min(first.getBlockZ(), second.getBlockZ())
        this.x2 = Math.max(first.getBlockX(), second.getBlockX())
        this.y2 = Math.max(first.getBlockY(), second.getBlockY())
        this.z2 = Math.max(first.getBlockZ(), second.getBlockZ())
    }

    Cuboid(Location location) {
        this(location, location)
    }

    Cuboid(Cuboid other) {
        this(other.getWorld().getName(), other.x1, other.y1, other.z1, other.x2, other.y2, other.z2)
    }

    Cuboid(JsonObject object) {
        this(
                new Location(Bukkit.getWorld(object.get("worldName").getAsString()), object.get("x1").getAsInt(), object.get("y1").getAsInt(), object.get("z1").getAsInt()),
                new Location(Bukkit.getWorld(object.get("worldName").getAsString()), object.get("x2").getAsInt(), object.get("y2").getAsInt(), object.get("z2").getAsInt())
        )
    }

    JsonObject toJson() {
        JsonObject object = new JsonObject()
        object.addProperty("worldName", worldName)
        object.addProperty("x1", x1)
        object.addProperty("y1", y1)
        object.addProperty("z1", z1)
        object.addProperty("x2", x2)
        object.addProperty("y2", y2)
        object.addProperty("z2", z2)

        return object
    }

    boolean hasBothPositionsSet() {
        return this.getMinimumPoint() != null && this.getMaximumPoint() != null
    }

    int getMinimumX() {
        return Math.min(this.x1, this.x2)
    }

    int getMinimumZ() {
        return Math.min(this.z1, this.z2)
    }

    int getMaximumX() {
        return Math.max(this.x1, this.x2)
    }

    int getMaximumZ() {
        return Math.max(this.z1, this.z2)
    }

    List<Vector> edges() {
        Location v1 = this.getMinimumPoint()
        Location v2 = this.getMaximumPoint()
        int minX = v1.getBlockX()
        int maxX = v2.getBlockX()
        int minZ = v1.getBlockZ()
        int startX = minZ + 1
        int maxZ = v2.getBlockZ()
        int capacity = (maxX - minX) * 4 + (maxZ - minZ) * 4
        capacity += 4
        List<Vector> result = new ArrayList<>(capacity)
        if(capacity <= 0) {
            return result
        }
        int minY = v1.getBlockY()
        int maxY = v1.getBlockY()
        for(int x = minX; x <= maxX; ++x) {
            result.add(new Vector(x, minY, minZ))
            result.add(new Vector(x, minY, maxZ))
            result.add(new Vector(x, maxY, minZ))
            result.add(new Vector(x, maxY, maxZ))
        }
        for(int z = startX; z < maxZ; ++z) {
            result.add(new Vector(minX, minY, z))
            result.add(new Vector(minX, maxY, z))
            result.add(new Vector(maxX, minY, z))
            result.add(new Vector(maxX, maxY, z))
        }
        return result
    }

    Set<Player> getPlayers() {
        Set<Player> players = new HashSet<>()
        for(Player player : Bukkit.getServer().getOnlinePlayers() ) {
            if(this.contains(player)) {
                players.add(player)
            }
        }
        return players
    }

    Location getLowerNE() {
        return new Location(this.getWorld(), (double) this.x1, (double) this.y1, (double) this.z1)
    }

    Location getUpperSW() {
        return new Location(this.getWorld(), (double) this.x2, (double) this.y2, (double) this.z2)
    }

    Location getCenter() {
        int x1 = this.x2 + 1
        int y1 = this.y2 + 1
        int z1 = this.z2 + 1
        return new Location(this.getWorld(), this.x1 + (x1 - this.x1) / 2.0, this.y1 + (y1 - this.y1) / 2.0, this.z1 + (z1 - this.z1) / 2.0)
    }

    World getWorld() {
        return Bukkit.getWorld(this.worldName)
    }

    int getSizeX() {
        return this.x2 - this.x1 + 1
    }

    int getSizeY() {
        return this.y2 - this.y1 + 1
    }

    int getSizeZ() {
        return this.z2 - this.z1 + 1
    }

    Location[] getCornerLocations() {
        Location[] result = new Location[8]
        Block[] cornerBlocks = this.getCornerBlocks()
        for(int i = 0; i < cornerBlocks.length; ++i) {
            result[i] = cornerBlocks[i].getLocation()
        }
        return result
    }

    Block[] getCornerBlocks() {
        Block[] result = new Block[8]
        World world = this.getWorld()
        result[0] = world.getBlockAt(this.x1, this.y1, this.z1)
        result[1] = world.getBlockAt(this.x1, this.y1, this.z2)
        result[2] = world.getBlockAt(this.x1, this.y2, this.z1)
        result[3] = world.getBlockAt(this.x1, this.y2, this.z2)
        result[4] = world.getBlockAt(this.x2, this.y1, this.z1)
        result[5] = world.getBlockAt(this.x2, this.y1, this.z2)
        result[6] = world.getBlockAt(this.x2, this.y2, this.z1)
        result[7] = world.getBlockAt(this.x2, this.y2, this.z2)
        return result
    }

    Cuboid expand(CuboidDirection dir, int amount) {
        switch(dir) {
            case CuboidDirection.NORTH: {
                return new Cuboid(this.worldName, this.x1 - amount, this.y1, this.z1, this.x2, this.y2, this.z2)
            }
            case CuboidDirection.SOUTH: {
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2 + amount, this.y2, this.z2)
            }
            case CuboidDirection.EAST: {
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1 - amount, this.x2, this.y2, this.z2)
            }
            case CuboidDirection.WEST: {
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2, this.z2 + amount)
            }
            case CuboidDirection.DOWN: {
                return new Cuboid(this.worldName, this.x1, this.y1 - amount, this.z1, this.x2, this.y2, this.z2)
            }
            case CuboidDirection.UP: {
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2 + amount, this.z2)
            }
            default: {
                throw new IllegalArgumentException("invalid direction " + dir)
            }
        }
    }

    Cuboid shift(CuboidDirection dir, int amount) {
        return this.expand(dir, amount).expand(dir.opposite(), -amount)
    }

    Cuboid outset(CuboidDirection dir, int amount) {
        switch (dir) {
            case CuboidDirection.HORIZONTAL: {
                return this.expand(CuboidDirection.NORTH, amount).expand(CuboidDirection.SOUTH, amount).expand(CuboidDirection.EAST, amount).expand(CuboidDirection.WEST, amount)
            }
            case CuboidDirection.VERTICAL: {
                return this.expand(CuboidDirection.DOWN, amount).expand(CuboidDirection.UP, amount)
            }
            case CuboidDirection.BOTH: {
                return this.outset(CuboidDirection.HORIZONTAL, amount).outset(CuboidDirection.VERTICAL, amount)
            }
            default: {
                throw new IllegalArgumentException("invalid direction " + dir)
            }
        }
    }

    Cuboid inset(CuboidDirection direction, int amount) {
        return this.outset(direction, -amount)
    }

    boolean contains(Cuboid cuboid) {
        return this.contains(cuboid.getMinimumPoint()) || this.contains(cuboid.getMaximumPoint())
    }

    boolean contains(Player player) {
        return this.contains(player.getLocation())
    }

    boolean contains(World world, int x, int z) {
        return (world == null || this.getWorld().equals(world)) && x >= this.x1 && x <= this.x2 && z >= this.z1 && z <= this.z2
    }

    boolean contains(int x, int y, int z) {
        return x >= this.x1 && x <= this.x2 && y >= this.y1 && y <= this.y2 && z >= this.z1 && z <= this.z2
    }

    boolean contains(Block block) {
        return this.contains(block.getLocation())
    }

    boolean contains(Location location) {
        if(location == null || this.worldName == null) {
            return false
        }
        World world = location.getWorld()
        return world != null && this.worldName.equals(location.getWorld().getName()) && this.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ())
    }

    int getVolume() {
        return this.getSizeX() * this.getSizeY() * this.getSizeZ()
    }

    int getArea() {
        Location min = this.getMinimumPoint()
        Location max = this.getMaximumPoint()
        return (max.getBlockX() - min.getBlockX() + 1) * (max.getBlockZ() - min.getBlockZ() + 1)
    }

    byte getAverageLightLevel() {
        long total = 0L
        int count = 0
        for(Block block : this) {
            if(block.isEmpty()) {
                total += block.getLightLevel()
                ++count
            }
        }
        return (count > 0) ? ((byte) (total / count)) : 0
    }

    Location getMinimumPoint() {
        return new Location(this.getWorld(), (double) Math.min(this.x1, this.x2), (double) Math.min(this.y1, this.y2), (double) Math.min(this.z1, this.z2))
    }

    Location getMaximumPoint() {
        return new Location(this.getWorld(), (double) Math.max(this.x1, this.x2), (double) Math.max(this.y1, this.y2), (double) Math.max(this.z1, this.z2))
    }

    Location getLocationOne() {
        return locationOne
    }

    Location getLocationTwo() {
        return locationTwo
    }

    int getWidth() {
        return this.getMaximumPoint().getBlockX() - this.getMinimumPoint().getBlockX()
    }

    int getHeight() {
        return this.getMaximumPoint().getBlockY() - this.getMinimumPoint().getBlockY()
    }

    int getLength() {
        return this.getMaximumPoint().getBlockZ() - this.getMinimumPoint().getBlockZ()
    }

    Cuboid contract() {
        return this.contract(CuboidDirection.DOWN).contract(CuboidDirection.SOUTH).contract(CuboidDirection.EAST).contract(CuboidDirection.UP).contract(CuboidDirection.NORTH).contract(CuboidDirection.WEST)
    }

    Cuboid contract(CuboidDirection direction) {
        Cuboid face = this.getFace(direction.opposite())
        switch(direction) {
            case DOWN: {
                while(face.containsOnly(Material.AIR) && face.y1 > this.y1) {
                    face = face.shift(CuboidDirection.DOWN, 1)
                }
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, face.y2, this.z2)
            }
            case UP: {
                while(face.containsOnly(Material.AIR) && face.y2 < this.y2) {
                    face = face.shift(CuboidDirection.UP, 1)
                }
                return new Cuboid(this.worldName, this.x1, face.y1, this.z1, this.x2, this.y2, this.z2)
            }
            case NORTH: {
                while(face.containsOnly(Material.AIR) && face.x1 > this.x1) {
                    face = face.shift(CuboidDirection.NORTH, 1)
                }
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, face.x2, this.y2, this.z2)
            }
            case SOUTH: {
                while(face.containsOnly(Material.AIR) && face.x2 < this.x2) {
                    face = face.shift(CuboidDirection.SOUTH, 1)
                }
                return new Cuboid(this.worldName, face.x1, this.y1, this.z1, this.x2, this.y2, this.z2)
            }
            case EAST: {
                while(face.containsOnly(Material.AIR) && face.z1 > this.z1) {
                    face = face.shift(CuboidDirection.EAST, 1)
                }
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2, face.z2)
            }
            case WEST: {
                while(face.containsOnly(Material.AIR) && face.z2 < this.z2) {
                    face = face.shift(CuboidDirection.WEST, 1)
                }
                return new Cuboid(this.worldName, this.x1, this.y1, face.z1, this.x2, this.y2, this.z2)
            }
            default: {
                throw new IllegalArgumentException("Invalid direction " + direction)
            }
        }
    }

    Cuboid getFace(CuboidDirection direction) {
        switch(direction) {
            case DOWN: {
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y1, this.z2)
            }
            case UP: {
                return new Cuboid(this.worldName, this.x1, this.y2, this.z1, this.x2, this.y2, this.z2)
            }
            case NORTH: {
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x1, this.y2, this.z2)
            }
            case SOUTH: {
                return new Cuboid(this.worldName, this.x2, this.y1, this.z1, this.x2, this.y2, this.z2)
            }
            case EAST: {
                return new Cuboid(this.worldName, this.x1, this.y1, this.z1, this.x2, this.y2, this.z1)
            }
            case WEST: {
                return new Cuboid(this.worldName, this.x1, this.y1, this.z2, this.x2, this.y2, this.z2)
            }
            default: {
                throw new IllegalArgumentException("Invalid direction " + direction)
            }
        }
    }

    boolean containsOnly(Material material) {
        for(Block block : this) {
            if(block.getType() != material) {
                return false
            }
        }
        return true
    }

    Cuboid getBoundingCuboid(Cuboid other) {
        if(other == null) {
            return this
        }
        int xMin = Math.min(this.x1, other.x1)
        int yMin = Math.min(this.y1, other.y1)
        int zMin = Math.min(this.z1, other.z1)
        int xMax = Math.max(this.x2, other.x2)
        int yMax = Math.max(this.y2, other.y2)
        int zMax = Math.max(this.z2, other.z2)
        return new Cuboid(this.worldName, xMin, yMin, zMin, xMax, yMax, zMax)
    }

    Block getRelativeBlock(int x, int y, int z) {
        return this.getWorld().getBlockAt(this.x1 + x, this.y1 + y, this.z1 + z)
    }

    Block getRelativeBlock(World world, int x, int y, int z) {
        return world.getBlockAt(this.x1 + x, this.y1 + y, this.z1 + z)
    }

    List<Chunk> getChunks() {
        World world = this.getWorld()
        int x1 = this.x1 & 0xFFFFFFF0
        int x2 = this.x2 & 0xFFFFFFF0
        int z1 = this.z1 & 0xFFFFFFF0
        int z2 = this.z2 & 0xFFFFFFF0
        List<Chunk> result = new ArrayList<>(x2 - x1 + 16 + (z2 - z1) * 16)
        for(int x3 = x1; x3 <= x2; x3 += 16) {
            for(int z3 = z1; z3 <= z2; z3 += 16) {
                result.add(world.getChunkAt(x3 >> 4, z3 >> 4))
            }
        }
        return result
    }

    Cuboid clone() {
        try {
            return (Cuboid) super.clone()
        } catch(CloneNotSupportedException e) {
            throw new RuntimeException("this could never happen", e)
        }
    }

    @Override
    String toString() {
        return "Cuboid: " + this.worldName + ',' + this.x1 + ',' + this.y1 + ',' + this.z1 + "=>" + this.x2 + ',' + this.y2 + ',' + this.z2
    }
}