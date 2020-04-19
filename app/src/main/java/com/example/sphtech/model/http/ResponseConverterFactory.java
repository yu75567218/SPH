package com.example.sphtech.model.http;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class ResponseConverterFactory extends Converter.Factory {

    private Gson gson;

    public static ResponseConverterFactory create() {
        return create(new Gson());
    }

    public static ResponseConverterFactory create(Gson gson) {
        return new ResponseConverterFactory(gson);
    }

    private ResponseConverterFactory(Gson gson) {
        if (gson == null) {
            throw new NullPointerException("gson can not null!");
        }
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> typeAdapter = gson.getAdapter(TypeToken.get(type));
        return new ResponseBodyConverter<>(typeAdapter);
    }

    public class ResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private TypeAdapter<T> typeAdapter;

        public ResponseBodyConverter(TypeAdapter<T> typeAdapter) {
            this.typeAdapter = typeAdapter;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            String json = value.string();
            value.close();
            ResponseBean bean = gson.fromJson(json, ResponseBean.class);
            if (!bean.success) {
                // 错误
                throw new ApiException(0, "异常");
            }
            // 成功
            return typeAdapter.fromJson(gson.toJson(bean.result));
        }
    }
}
