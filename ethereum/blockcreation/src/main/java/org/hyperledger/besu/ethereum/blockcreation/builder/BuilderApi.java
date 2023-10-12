package org.hyperledger.besu.ethereum.blockcreation.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.hyperledger.besu.datatypes.BLSPublicKey;
import org.hyperledger.besu.datatypes.Hash;
import org.hyperledger.besu.ethereum.core.Block;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.IOException;


/**
 * Builder API service
 */
public class BuilderApi {

    private static final Logger LOG = LoggerFactory.getLogger(BuilderApi.class);

    OkHttpClient client;
    String endpoint;

    BLSPublicKey publicKey;

    ObjectMapper mapper = new ObjectMapper();

    BuilderApi(String endpoint, String blsPublicKeyHex) {
        this.endpoint = endpoint;
        this.client = new OkHttpClient();
        this.publicKey = BLSPublicKey.fromHexString(blsPublicKeyHex);
    }
    Block getBlock(final long slot, final Hash parentHash) throws Exception {
        Request request = new Request.Builder()
                .addHeader("Accept", "application/json; q=0.5")
                .url(String.format("%s/eth/v1/builder/block/%d/%s/%s", this.endpoint,
                        slot, parentHash.toHexString(), this.publicKey.toHexString())).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Block Request failed: " + response);
            String jsonString = response.body().string();
        }
    }
}
