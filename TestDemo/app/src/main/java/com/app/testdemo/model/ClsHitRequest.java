package com.app.testdemo.model;

import com.google.gson.annotations.SerializedName;

public class ClsHitRequest {

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("title")
    private String title;

    @SerializedName("url")
    private String url;

    @SerializedName("author")
    private String author;

    @SerializedName("points")
    private Integer points;

    @SerializedName("story_text")
    private Object storyText;

    @SerializedName("comment_text")
    private Object commentText;

    @SerializedName("num_comments")
    private Integer numComments;

    @SerializedName("story_id")
    private Object storyId;

    @SerializedName("story_title")
    private Object storyTitle;

    @SerializedName("story_url")
    private Object storyUrl;

    @SerializedName("parent_id")
    private Object parentId;


    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Object getStoryText() {
        return storyText;
    }

    public void setStoryText(Object storyText) {
        this.storyText = storyText;
    }

    public Object getCommentText() {
        return commentText;
    }

    public void setCommentText(Object commentText) {
        this.commentText = commentText;
    }

    public Integer getNumComments() {
        return numComments;
    }

    public void setNumComments(Integer numComments) {
        this.numComments = numComments;
    }

    public Object getStoryId() {
        return storyId;
    }

    public void setStoryId(Object storyId) {
        this.storyId = storyId;
    }

    public Object getStoryTitle() {
        return storyTitle;
    }

    public void setStoryTitle(Object storyTitle) {
        this.storyTitle = storyTitle;
    }

    public Object getStoryUrl() {
        return storyUrl;
    }

    public void setStoryUrl(Object storyUrl) {
        this.storyUrl = storyUrl;
    }

    public Object getParentId() {
        return parentId;
    }

    public void setParentId(Object parentId) {
        this.parentId = parentId;
    }

}