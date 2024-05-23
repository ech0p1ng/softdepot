package ru.softdepot.core.models;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Program {
    private int id;
    private String name;
    private BigDecimal price;
    private String fullDescription;
    private int developerId;
    private String shortDescription;
    private List<Tag> tags;

    private float averageEstimation;
    private String filesPath;
    private String nameForPath;

    public Program(int id, String name, BigDecimal price, String fullDescription,
                   int developerId, String shortDescription, List<Tag> tags) {
        this.id = id;
        this.price = price;
        this.fullDescription = fullDescription;
        this.developerId = developerId;
        this.shortDescription = shortDescription;
        this.tags = tags;
        this.filesPath = "/program/id" + id;
        setName(name);
    }

    public Program(String name, BigDecimal price, String description,
                   int developerId, String shortDescription, List<Tag> tags) {
        this.price = price;
        this.fullDescription = description;
        this.developerId = developerId;
        this.shortDescription = shortDescription;
        this.tags = tags;
        this.filesPath = "/program/id" + id;
        setName(name);
    }

    public Program(String name, BigDecimal price, String description,
                   int developerId, String shortDescription) {
        this.price = price;
        this.fullDescription = description;
        this.developerId = developerId;
        this.shortDescription = shortDescription;
        this.filesPath = "/program/id" + id;
        setName(name);
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
        this.nameForPath = name.replaceAll(" ", "_");
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

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
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

    public String getNameForPath() {
        return nameForPath;
    }


    public String getLogoUrl() {
        return filesPath + "/logo.png";
    }

    public String getInstallerWindowsUrl() {
        String path = filesPath + "/installers/win";

        File dir = new File(path);
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            return files[0].getAbsolutePath();
        }
        return null;
    }

    public String getInstallerLinuxUrl() {
        String path = filesPath + "/installers/linux";

        File dir = new File(path);
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            return files[0].getAbsolutePath();
        }
        return null;
    }

    public String getInstallerMacosUrl() {
        String path = filesPath + "/installers/macos";

        File dir = new File(path);
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            return files[0].getAbsolutePath();
        }
        return null;
    }

    public List<String> getScreenshotsUrl() {
        return new ArrayList<String>(Arrays.asList(
                String.format("/program/id%d/screenshots/sh01.jpg", getId()),
                String.format("/program/id%d/screenshots/sh02.jpg", getId()),
                String.format("/program/id%d/screenshots/sh03.jpg", getId()),
                String.format("/program/id%d/screenshots/sh04.jpg", getId()),
                String.format("/program/id%d/screenshots/sh05.jpg", getId()),
                String.format("/program/id%d/screenshots/sh06.jpg", getId()),
                String.format("/program/id%d/screenshots/sh07.jpg", getId()),
                String.format("/program/id%d/screenshots/sh08.jpg", getId()),
                String.format("/program/id%d/screenshots/sh09.jpg", getId()),
                String.format("/program/id%d/screenshots/sh10.jpg", getId())
        ));
    }

    public String getHeaderUrl() {
        return filesPath + "/header.jpg";
    }
}
