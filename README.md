
# Global Waves

### Author: Nichita-Adrian Bunu, 323CA  Facultatea de Automatica si Calculatoare UNSTPB
**Contact:** [nichita_adrian.bunu@stud.acs.upb.ro](mailto:nichita_adrian.bunu@stud.acs.upb.ro)

---

## Overview

Global Waves is a Java project designed to simulate the diverse functionalities
of a global music streaming platform, inspired by services like Spotify. This
simulation introduces various user interactions, including features like
artists, hosts, playlists, and more. Users can explore, play, and interact with a wide range of audio collections, artists, and hosts.

## Entities

### Artist Page

Each artist has a dedicated page on the platform, allowing them to manage
details such as albums, merchandise, and events. Normal users can explore and
access these details on the artist's page.

### Host Page

Similar to the artist page, a host's page showcases podcasts and announcements. Normal users can explore and access these details on the host's page.

### Commands for Page System (Normal Users Only)

- **ChangePage:** View the page of a specific user (artist or host) using a formatted string for each page.

## User Types

Three user types exist, each with specific commands:

- **Artist**
- **Host**
- **Normal**

## Audio File Collections

- **Library:** All songs available on the platform, accessible to all users.
- **Playlist:** A collection of songs created by a user, public or private.
- **Podcast:** A collection of episodes with a specified order.
- **Album:** A collection of songs created by an artist. Normal users can
search and listen to albums.

## Search Bar & Select

Allows users to search for songs, playlists, podcasts and albums based on
various filters and select them afterwards.

## Music Player

Users can interact with a music player to play, pause, shuffle, repeat, and navigate through tracks. The player supports songs, albums and playlists.

## Commands for Admin

- **AddUser:** Admins can add new users (normal, artists, or hosts) with basic details.
- **DeleteUser:** Admins can delete user accounts.

## Artist Commands

- **AddAlbum:** Artists can add albums to the platform.
- **RemoveAlbum:** Artists can remove albums from the platform.
- **AddEvent:** Artists can add events to their profile.
- **RemoveEvent:** Artists can remove events from their profile.
- **AddMerch:** Artists can add merchandise items to the platform.

## Host Commands

- **AddPodcast:** Hosts can add podcasts to the platform.
- **AddAnnouncement:** Hosts can add announcements to their profile.
- **RemoveAnnouncement:** Hosts can remove announcements from their profile.

## Normal User Commands

- **SwitchConnectionStatus:** Users can switch between online and offline modes.
- **List of Commands Restricted in Offline Mode:**
  - Search, Select, Load, PlayPause, Repeat, Shuffle, Forward, Backward, Like, Next, Prev, CreatePlaylist, AddRemoveInPlaylist, SwitchVisibility, Follow, ChangePage, PrintCurrentPage.

## General Statistics Commands

- **GetTop5Albums:** Display the top 5 albums based on likes.
- **GetTop5Artists:** Display the top 5 artists based on likes.
- **GetAllUsers:** Show the names of all users in the application.
- **GetOnlineUsers:** Show the names of online normal users.

## Timestamp

Commands include a timestamp field to simulate real-time application behavior.

## Commands Player

- **Load:** Load a source (song, playlist, or podcast) into the player.
- **PlayPause:** Toggle between play and pause.
- **Repeat:** Toggle repeat mode.
- **Shuffle:** Toggle shuffle mode.
- **Forward/Backward:** Navigate forward or backward in a podcast.
- **Like:** Like or unlike the current song.
- **Next/Prev:** Skip to the next or return to the previous track.
- **AddRemoveInPlaylist:** Add or remove the current song from a playlist.
- **Status:** Display the current status of the player.

## Commands Playlist

- **CreatePlaylist:** Create a new playlist.
- **SwitchVisibility:** Switch a playlist between public and private.
- **FollowPlaylist:** Follow or unfollow a playlist.
- **ShowPlaylists:** Display all playlists owned by an user.
