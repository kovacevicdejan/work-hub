package com.example.workhub

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

interface Destination {
    val icon: ImageVector
    val route: String
}

object HomeDestination : Destination {
    override val icon = Icons.Default.Lock
    override val route = "Home"
}

object NetworkDestination : Destination {
    override val icon = Icons.Default.Phone
    override val route = "Network"
}

object ProfileDestination : Destination {
    override val icon = Icons.Default.Person
    override val route = "Profile"
}

object JobsDestination : Destination {
    override val icon = Icons.Default.ShoppingCart
    override val route = "Jobs"
}

object ChatsDestination : Destination {
    override val icon = Icons.Default.Email
    override val route = "Chats"
}

val snippetDestinations = listOf(HomeDestination, NetworkDestination, ProfileDestination, JobsDestination, ChatsDestination)
