package com.example.demo;

import javax.persistence.*;


    @Entity
    @Table (name="Item")
    public class Item {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private String name;
        private String description;
        private String category;
        private String location;
        private int qty;
        private String deliveryType; //jpa översätter det stora T:et till "_" då camelcase inte används i sql. Det går öven att lösa med @Column annoteringen i Item klassen.
        private String image;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "account_orgnr", nullable = false)
        private Account account;
        public Item() {
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public String getDeliveryType() {
            return deliveryType;
        }

        public void setDeliveryType(String deliveryType) {
            this.deliveryType = deliveryType;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Item(String name, String description, String category, String location, int qty, String deliveryType, String image) {
            this.name = name;
            this.description = description;
            this.category = category;
            this.location = location;
            this.qty = qty;
            this.deliveryType = deliveryType;
            this.image = image;
        }
    }
