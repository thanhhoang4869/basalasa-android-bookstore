package com.example.basalasa.model.body

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import okio.Okio
import okio.Source
import java.io.IOException
import java.io.InputStream


class FileRequestBody : RequestBody() {
    private var inputStream: InputStream? = null
    private var type: MediaType? = null
    fun UploadFileRequestBody(inputStream: InputStream?, type: MediaType?) {
        this.inputStream = inputStream
        this.type = type
    }

    override fun contentType(): MediaType? {
        return type
    }

    @Throws(IOException::class)
    override fun contentLength(): Long {
        return inputStream?.available()!!.toLong()
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        var source: Source? = null
        try {
            source = Okio.source(inputStream)
            sink.writeAll(source)
        } catch (e: Exception) {
            if (source != null) {
                source.close()
            }
        }
    }
}
