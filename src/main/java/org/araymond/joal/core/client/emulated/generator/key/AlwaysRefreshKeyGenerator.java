package org.araymond.joal.core.client.emulated.generator.key;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.turn.ttorrent.common.protocol.TrackerMessage.AnnounceRequestMessage.RequestEvent;
import org.araymond.joal.core.client.emulated.generator.key.type.KeyTypes;
import org.araymond.joal.core.ttorent.client.MockedTorrent;

/**
 * Created by raymo on 16/07/2017.
 */
public class AlwaysRefreshKeyGenerator extends KeyGenerator {

    @JsonCreator
    AlwaysRefreshKeyGenerator(
            @JsonProperty(value = "length", required = true) final Integer length,
            @JsonProperty(value = "type", required = true) final KeyTypes type,
            @JsonProperty(value = "upperCase", required = true) final boolean upperCase,
            @JsonProperty(value = "lowerCase", required = true) final boolean lowerCase
    ) {
        super(length, type, upperCase, lowerCase);
    }

    @Override
    public String getKey(final MockedTorrent torrent, final RequestEvent event) {
        return super.generateKey();
    }

}
