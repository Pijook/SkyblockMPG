package pl.pijok.skyblock.island;

import java.util.UUID;

public class Invite {

    private final Long creationDate;
    private final UUID islandID;
    private final UUID targetID;

    public Invite(Long creationDate, UUID islandID, UUID targetID) {
        this.creationDate = creationDate;
        this.islandID = islandID;
        this.targetID = targetID;
    }

    public Long getCreationDate() {
        return creationDate;
    }

    public UUID getIslandID() {
        return islandID;
    }

    public UUID getTargetID() {
        return targetID;
    }
}
