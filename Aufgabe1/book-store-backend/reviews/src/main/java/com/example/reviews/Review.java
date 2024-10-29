    package com.example.reviews;

    public class Review {
        private Long id;
        private String reviewer;
        private String text;

        public Review(Long id, String reviewer, String text) {
            this.id = id;
            this.reviewer = reviewer;
            this.text = text;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getReviewer() {
            return reviewer;
        }

        public void setReviewer(String reviewer) {
            this.reviewer = reviewer;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
