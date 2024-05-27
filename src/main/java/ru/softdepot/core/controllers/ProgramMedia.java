package ru.softdepot.core.controllers;

import org.springframework.web.multipart.MultipartFile;

public class ProgramMedia {
    MultipartFile logo;
    MultipartFile programHeaderImg;
    MultipartFile[] screenshots;
    MultipartFile winInstaller;
    MultipartFile linuxInstaller;
    MultipartFile macosInstaller;

    public ProgramMedia(MultipartFile logo, MultipartFile programHeaderImg, MultipartFile[] screenshots, MultipartFile winInstaller, MultipartFile linuxInstaller, MultipartFile macosInstaller) {
        this.logo = logo;
        this.programHeaderImg = programHeaderImg;
        this.screenshots = screenshots;
        this.winInstaller = winInstaller;
        this.linuxInstaller = linuxInstaller;
        this.macosInstaller = macosInstaller;
    }

    public ProgramMedia() {
    }

    public MultipartFile getLogo() {
        return logo;
    }

    public void setLogo(MultipartFile logo) {
        this.logo = logo;
    }

    public MultipartFile getProgramHeaderImg() {
        return programHeaderImg;
    }

    public void setProgramHeaderImg(MultipartFile programHeaderImg) {
        this.programHeaderImg = programHeaderImg;
    }

    public MultipartFile[] getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(MultipartFile[] screenshots) {
        this.screenshots = screenshots;
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
}