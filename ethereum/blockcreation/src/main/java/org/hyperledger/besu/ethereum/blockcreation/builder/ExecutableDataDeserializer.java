package org.hyperledger.besu.ethereum.blockcreation.builder;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.tuweni.units.bigints.UInt64;
import org.apache.tuweni.units.bigints.UInt64Value;
import org.hyperledger.besu.datatypes.Hash;

public class ExecutableDataDeserializer extends StdDeserializer<ExecutableData> {

    public ExecutableDataDeserializer() {
        this(null);
    }

    public ExecutableDataDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ExecutableData deserialize(JsonParser parser, DeserializationContext deserializer) throws java.io.IOException{
        ExecutableData executableData = new ExecutableData();
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        executableData.parentHash = Hash.fromHexString(node.get("parentHash").asText());
        executableData.feeRecipient = Hash.fromHexString(node.get("feeRecipient").asText());
        executableData.stateRoot = Hash.fromHexString(node.get("stateRoot").asText());
        executableData.receiptsRoot = Hash.fromHexString(node.get("receiptsRoot").asText()):
        executableData.logsBloom = node.get("logsBloom").binaryValue();
        executableData.prevRandao = UInt64.valueOf(node.get("prevRandao").asLong());
        executableData.number = UInt64.valueOf(node.get("number").asLong());
        executableData.gasLimit = UInt64.valueOf(node.get("gasLimit").asLong());
        executableData.gasUsed = UInt64.valueOf(node.get("gasUsed").asLong());
        executableData.timestamp = UInt64.valueOf(node.get("timestamp").asLong());
        executableData.extraData = node.get("extraData").binaryValue();
        executableData.baseFeePerGas = node.get("baseFeePerGas").bigIntegerValue();
        executableData.blockHash = Hash.fromHexString(node.get("blockHash").asText());

        byte[][] transactions = new byte[node.get("transactions").size()][];

        for (int i = 0; i < node.get("transactions").size(); i++) {
            transactions[i] = node.get("transactions").get(i).binaryValue();
        }

        executableData.transactions = transactions;
        executableData.blobGasUsed = node.get
    }
}
