package ru.softdepot.core.models;

import java.math.BigDecimal;
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

    private float averageEstimation;

    public Program(int id, String name, BigDecimal price, String description, String logoUrl, String installerWindowsUrl, String installerLinuxUrl, String installerMacosUrl, List<String> screenshotsUrl, int developerId) {
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
    }

    public Program(int developerId, String name, String description, BigDecimal price, String logoUrl, String installerWindowsUrl, String installerLinuxUrl, String installerMacosUrl, List<String> screenshotsUrl) {
        this.developerId = developerId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.logoUrl = logoUrl;
        this.installerWindowsUrl = installerWindowsUrl;
        this.installerLinuxUrl = installerLinuxUrl;
        this.installerMacosUrl = installerMacosUrl;
        this.screenshotsUrl = screenshotsUrl;
    }

    public Program(){}

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
        return screenshotsUrl;
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
}
