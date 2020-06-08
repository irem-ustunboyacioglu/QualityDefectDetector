package com.qualitydefectdetector.enums;

public enum CriteriaType {
    WELL_FORMED("İyi Biçimlendirilmiş","Bir kullanıcı hikayesi role ve amaca sahip olmalıdır."),
    ATOMIC("Atomik","Bir kullanıcı hikayesi sadece bir özellikle alakalı olmalıdır."),
    MINIMAL("Minimal","Bir kullanıcı hikayesi herhangi açıklama, not içermemelidir."),
    FULL_SENTENCE("Tam Cümle","Bir kullanıcı hikayesi cümle kurallarına uymalıdır."),
    SPELLING("Yazım","Bir kullanıcı hikayesi yanlış yazılmış kelime içermemelidir."),
    UNIFORM("Tek Biçimli","Tüm kullanıcı hikayeleri aynı formata sahip olmalıdır."),
    UNIQUE("Eşi ve Benzeri Olmayan","Hiçbir kullanıcı hikayesi başka bir kullanıcı hikayesiyle aynı olmamalıdır."),
    PROBLEM_ORIENTED("Probleme Yönelik","Kullanıcı hikayeleri çözüm belirtmemelidir."),
    CONFLICT_FREE("Zıtlık İçermeyen","Kullanıcı hikayeleri arasında anlamsal olarak zıtlık olmamalıdır."),
    INDEPENDENT("Bağımsız","Kullanıcı hikayeleri birbirlerinden bağımsız olmalıdır.");

    private String displayName;
    private String description;

    CriteriaType(String displayName,String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }
}
