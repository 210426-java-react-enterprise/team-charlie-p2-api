package com.revature.pantry.web.dtos;

import com.fasterxml.jackson.annotation.*;
import javax.validation.constraints.NotNull;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class RecipeDTO {

        @NotNull
        private String label;
        @NotNull
        private int calories;
        @NotNull
        private int yield;
        @NotNull
        private String url;
        @NotNull
        private String image;

        public RecipeDTO(){
            super();
        }

        public RecipeDTO(String label, int calories, int yield, String url, String image) {
            this.label = label;
            this.calories = calories;
            this.yield = yield;
            this.url = url;
            this.image = image;
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

    }

