package com.example.workhub

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

interface Destination {
    val icon: ImageVector
    val route: String
}

object HomeDestination : Destination {
    override val icon = Icons.Filled.Home
    override val route = "Home"
}

object NetworkDestination : Destination {
    override val icon = Icons.Default.Phone
    override val route = "Network"
}

object PostDestination : Destination {
    override val icon = Icons.Default.Add
    override val route = "Post"
}

object JobsDestination : Destination {
    override val icon = Icons.Default.ShoppingCart
    override val route = "Jobs"
}

object ChatsDestination : Destination {
    override val icon = Icons.Default.Email
    override val route = "Chats"
}

val snippetDestinations = listOf(HomeDestination, NetworkDestination, PostDestination, JobsDestination, ChatsDestination)
