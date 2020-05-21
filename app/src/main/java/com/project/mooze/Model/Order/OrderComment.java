package com.project.mooze.Model.Order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderComment {
    @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;

    public OrderComment(String content) {
        this.content = content;
    }

    public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }
}
