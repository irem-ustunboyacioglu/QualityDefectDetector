package com.qualitydefectdetector.enums;

public enum CriteriaType {
    WELL_FORMED("İyi Biçimlendirilmiş"),
    ATOMIC("Atomik"),
    MINIMAL("Minimal"),
    FULL_SENTENCE("Tam Cümle"),
    SPELLING("Yazım"),
    UNIFORM("Tek Biçimli"),
    UNIQUE("Eşi ve Benzeri Olmayan"),
    PROBLEM_ORIENTED("Probleme Yönelik"),
    CONFLICT_FREE("Zıtlık İçermeyen"),
    INDEPENDENT("Bağımsız");

    private String displayName;

    CriteriaType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
