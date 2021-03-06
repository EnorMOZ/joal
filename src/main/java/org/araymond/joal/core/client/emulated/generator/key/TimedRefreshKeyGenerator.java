package org.araymond.joal.core.client.emulated.generator.key;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.turn.ttorrent.common.protocol.TrackerMessage.AnnounceRequestMessage.RequestEvent;
import org.araymond.joal.core.client.emulated.TorrentClientConfigIntegrityException;
import org.araymond.joal.core.client.emulated.generator.key.type.KeyTypes;
import org.araymond.joal.core.ttorent.client.MockedTorrent;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by raymo on 16/07/2017.
 */
public class TimedRefreshKeyGenerator extends KeyGenerator {

    private LocalDateTime lastGeneration;
    private String key;
    private final Integer refreshEvery;

    @JsonCreator
    TimedRefreshKeyGenerator(
            @JsonProperty(value = "refreshEvery", required = true) final Integer refreshEvery,
            @JsonProperty(value = "length", required = true) final Integer length,
            @JsonProperty(value = "type", required = true) final KeyTypes type,
            @JsonProperty(value = "upperCase", required = true) final boolean upperCase,
            @JsonProperty(value = "lowerCase", required = true) final boolean lowerCase
    ) {
        super(length, type, upperCase, lowerCase);
        if (refreshEvery == null || refreshEvery < 1) {
            throw new TorrentClientConfigIntegrityException("refreshEvery must be greater than 0.");
        }
        this.refreshEvery = refreshEvery;
    }

    @JsonProperty("refreshEvery")
    Integer getRefreshEvery() {
        return refreshEvery;
    }

    @Override
    public String getKey(final MockedTorrent torrent, final RequestEvent event) {
        if (this.shouldRegenerateKey()) {
            this.lastGeneration = LocalDateTime.now();
            this.key = super.generateKey();
        }

        return this.key;
    }

    private boolean shouldRegenerateKey() {
        return this.lastGeneration == null || ChronoUnit.SECONDS.between(this.lastGeneration, LocalDateTime.now()) >= this.refreshEvery;
    }
}
