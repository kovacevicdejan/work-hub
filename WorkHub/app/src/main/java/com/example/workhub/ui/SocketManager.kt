package com.example.workhub.ui

import android.app.Application
import com.example.workhub.data.repository.LocalMessageRepository
import com.example.workhub.data.retrofit.BASE_URL
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import java.net.URISyntaxException
import javax.inject.Inject

object SocketManager: Application() {
    private var socket: Socket? = null

    init {
        try {
            socket = IO.socket(BASE_URL)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }

    fun connect() {
        socket?.connect()
    }

    fun disconnect() {
        socket?.disconnect()
    }

    fun isConnected(): Boolean {
        return socket?.connected() ?: false
    }

    fun onMessageReceived(listener: (String) -> Unit) {
        getSocket()?.on("chat message") { args ->
            val message = args[0] as String
            listener.invoke(message)
        }
    }

    fun sendMessage(message: String) {
        socket?.emit("message", message)
    }

    fun getSocket(): Socket? {
        return socket
    }

    fun getEmitterListener(event: String, listener: Emitter.Listener): Emitter? {
        return getSocket()!!.on(event, listener)
    }
}
