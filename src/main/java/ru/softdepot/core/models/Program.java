package ru.softdepot.core.models;

import org.springframework.web.multipart.MultipartFile;

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

    private MultipartFile winInstaller;
    private MultipartFile linuxInstaller;
    private MultipartFile macosInstaller;
    private MultipartFile headerImg;
    private MultipartFile[] screenshots;
    private MultipartFile logo;

    private String headerUrl;

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

    public Program() {}

    public Program(String name, String shortDescription, String fullDescription,
                   BigDecimal price, MultipartFile logo, MultipartFile winInstaller,
                   MultipartFile linuxInstaller, MultipartFile macosInstaller,
                   MultipartFile headerImg, MultipartFile[] screenshots) {
        setName(name);
        setShortDescription(shortDescription);
        setFullDescription(fullDescription);
        setPrice(price);
        setLogo(logo);
        setWinInstaller(winInstaller);
        setLinuxInstaller(linuxInstaller);
        setMacosInstaller(macosInstaller);
        setHeaderImg(headerImg);
        setScreenshots(screenshots);
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

    public MultipartFile getWinInstaller() {
        return winInstaller;
    }

    public void setWinInstaller(MultipartFile winInstaller) {
        this.winInstaller = winInstaller;
    }

    public MultipartFile getLinuxInstaller() {
        return linuxInstaller;
    }

    public void setLinuxInstaller(MultipartFile linuxInstaller) {
        this.linuxInstaller = linuxInstaller;
    }

    public MultipartFile getMacosInstaller() {
        return macosInstaller;
    }

    public void setMacosInstaller(MultipartFile macosInstaller) {
        this.macosInstaller = macosInstaller;
    }

    public MultipartFile getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(MultipartFile headerImg) {
        this.headerImg = headerImg;
    }

    public MultipartFile[] getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(MultipartFile[] screenshots) {
        this.screenshots = screenshots;
    }

    public MultipartFile getLogo() {
        return logo;
    }

    public void setLogo(MultipartFile logo) {
        this.logo = logo;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }

    public String getHeaderUrl() {
        return headerUrl;
    }
}
