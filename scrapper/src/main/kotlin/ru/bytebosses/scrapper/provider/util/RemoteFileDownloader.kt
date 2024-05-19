package ru.bytebosses.scrapper.provider.util

import java.io.FileOutputStream
import java.net.URI
import java.nio.channels.Channels
import java.nio.channels.ReadableByteChannel
import java.nio.file.Path


object RemoteFileDownloader {

    fun download(url: String, destination: Path) {
        val file = URI(url).toURL()
        val rbc: ReadableByteChannel = Channels.newChannel(file.openStream())
        val fos = FileOutputStream(destination.toFile())
        fos.channel.transferFrom(rbc, 0, Long.MAX_VALUE)
    }

}
