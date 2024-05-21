package ru.softdepot.core.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Program {
    private int id;
    private String name;
    private BigDecimal price;
    private String description;
    private String logoUrl;
    private String installerWindowsUrl;
    private String installerLinuxUrl;
    private String installerMacosUrl;
    private List<String> screenshotsUrl;
    private int developerId;
    private String shortDescription;
    private String headerUrl;
    private List<Tag> tags;

    private float averageEstimation;

    public Program(int id, String name, BigDecimal price, String description, String logoUrl, String installerWindowsUrl, String installerLinuxUrl, String installerMacosUrl, List<String> screenshotsUrl, int developerId, String shortDescription, List<Tag> tags) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.logoUrl = logoUrl;
        this.installerWindowsUrl = installerWindowsUrl;
        this.installerLinuxUrl = installerLinuxUrl;
        this.installerMacosUrl = installerMacosUrl;
        this.screenshotsUrl = screenshotsUrl;
        this.developerId = developerId;
        this.shortDescription = shortDescription;
        this.tags = tags;
    }

    public Program(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getPriceAsString() {
        if (getPrice().compareTo(BigDecimal.ZERO) > 0){
            String priceStr = getPrice().toString() + " руб.";
            priceStr = priceStr.replace(".00", "");
            return priceStr;
        }
        else {
            return "Бесплатно";
        }

    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getInstallerWindowsUrl() {
        return installerWindowsUrl;
    }

    public void setInstallerWindowsUrl(String installerWindowsUrl) {
        this.installerWindowsUrl = installerWindowsUrl;
    }

    public String getInstallerLinuxUrl() {
        return installerLinuxUrl;
    }

    public void setInstallerLinuxUrl(String installerLinuxUrl) {
        this.installerLinuxUrl = installerLinuxUrl;
    }

    public String getInstallerMacosUrl() {
        return installerMacosUrl;
    }

    public void setInstallerMacosUrl(String installerMacosUrl) {
        this.installerMacosUrl = installerMacosUrl;
    }

    public List<String> getScreenshotsUrl() {
//        return screenshotsUrl;
        List<String> screenshots = Arrays.asList(
                String.format("/program/id%d/screenshots/sh01.jpg", id),
                String.format("/program/id%d/screenshots/sh02.jpg", id),
                String.format("/program/id%d/screenshots/sh03.jpg", id),
                String.format("/program/id%d/screenshots/sh04.jpg", id),
                String.format("/program/id%d/screenshots/sh05.jpg", id),
                String.format("/program/id%d/screenshots/sh06.jpg", id),
                String.format("/program/id%d/screenshots/sh07.jpg", id),
                String.format("/program/id%d/screenshots/sh08.jpg", id),
                String.format("/program/id%d/screenshots/sh09.jpg", id),
                String.format("/program/id%d/screenshots/sh10.jpg", id)
        );
        return screenshots;
    }

    public void setScreenshotsUrl(List<String> screenshotsUrl) {
        this.screenshotsUrl = screenshotsUrl;
    }

    public void setScreenshotsUrl(String... screenshotsUrl) {
        this.screenshotsUrl = Arrays.stream(screenshotsUrl).toList();
    }

    public int getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(int developerId) {
        this.developerId = developerId;
    }

    public float getAverageEstimation() {
        return averageEstimation;
    }

    public void setAverageEstimation(float averageEstimation) {
        this.averageEstimation = averageEstimation;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getHeaderUrl(){
        return String.format("/program/id%d/header.jpg", id);
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getTagsAsString() {
        List<String> tagNameList = new ArrayList<>();
        if (tags == null)
            return "";
        for (Tag tag : tags) {
            tagNameList.add(tag.getName());
        }

        return String.join(", ", tagNameList);
    }
}
