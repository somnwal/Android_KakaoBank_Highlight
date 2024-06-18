package com.somnwal.android.kakaobank.app.core.datastore.util

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.somnwal.kakaobank.app.core.datastore.KakaoPreferences
import java.io.InputStream
import java.io.OutputStream

object KakaoPreferencesSerializer : Serializer<KakaoPreferences> {
    override val defaultValue: KakaoPreferences
        get() = KakaoPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): KakaoPreferences =
        try {
            KakaoPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }

    override suspend fun writeTo(t: KakaoPreferences, output: OutputStream) =
        t.writeTo(output)

}