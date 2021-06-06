package com.revature.pantry.web.dtos;

import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class RecipeDTO {

        @JsonProperty("label")
        private String label;

        @JsonProperty("calories")
        private int calories;

        @JsonProperty("yield")
        private int yield;

        @JsonProperty("url")
        private String url;

        @JsonProperty("image")
        private String image;

        public RecipeDTO(){
            super();
        }

        public String getLabel() {
            return label;
        }

        public int getCalories() {
            return calories;
        }

        public int getYield() {
            return yield;
        }

        public String getUrl() {
            return url;
        }

        public String getImage() {
            return image;
        }

        @Override
        public String toString() {
            return "RecipeDTO{" +
                    "label='" + label + '\'' +
                    ", calories=" + calories +
                    ", yield=" + yield +
                    ", url='" + url + '\'' +
                    ", image='" + image + '\'' +
                    '}';
        }
    }

