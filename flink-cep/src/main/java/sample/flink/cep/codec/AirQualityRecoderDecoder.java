package sample.flink.cep.codec;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import com.alibaba.fastjson.JSON;

import sample.flink.cep.entity.AirQualityRecoder;

@SuppressWarnings("serial")
public class AirQualityRecoderDecoder implements DeserializationSchema<AirQualityRecoder> {

    private static Charset charset = Charset.forName("UTF-8");

    @Override
    public TypeInformation<AirQualityRecoder> getProducedType() {
        return TypeInformation.of(new TypeHint<AirQualityRecoder>() {
        });
    }

    @Override
    public AirQualityRecoder deserialize(byte[] message) throws IOException {
        return JSON.parseObject(new String(message, charset), AirQualityRecoder.class);
    }

    @Override
    public boolean isEndOfStream(AirQualityRecoder nextElement) {
        return false;
    }

}
