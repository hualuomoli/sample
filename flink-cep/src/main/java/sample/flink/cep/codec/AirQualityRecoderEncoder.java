package sample.flink.cep.codec;

import java.nio.charset.Charset;

import org.apache.flink.api.common.serialization.SerializationSchema;

import com.alibaba.fastjson.JSON;

import sample.flink.cep.entity.AirQualityRecoder;

@SuppressWarnings("serial")
public class AirQualityRecoderEncoder implements SerializationSchema<AirQualityRecoder> {

    private static Charset charset = Charset.forName("UTF-8");

    @Override
    public byte[] serialize(AirQualityRecoder element) {
        return JSON.toJSONString(element).getBytes(charset);
    }

}
