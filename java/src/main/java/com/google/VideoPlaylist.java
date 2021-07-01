package com.google;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/** A class used to represent a Playlist */
class VideoPlaylist implements Comparable{

    private final String name;
    public List<Video> p_videos;

    VideoPlaylist(String playlistName) {
        this.name = playlistName;
        this.p_videos = new ArrayList<>();
    }

    /** Returns the title of the video. */
    String getName() {
        return name;
    }

    public void addPlaylistVideo(String title, String id, List<String> tags)
    {

    }

    /**
     *
     * @return list of all videos
     */
    List<Video> getPlaylistVideos() {
        //ArrayList<Video> vids = new ArrayList<>(this.p_videos.values());
        //Collections.sort(vids);
        //return vids;

        return p_videos;
    }

    /**
     * Get a video by id. Returns null if the video is not found.
     */
    //Video getPlaylistVideo(String videoId) {
       // return this.p_videos.get(videoId);
   // }

    @Override
    public int compareTo(Object o) {
        VideoPlaylist v = (VideoPlaylist) o;
        return this.name.compareTo(v.name);
    }

}
