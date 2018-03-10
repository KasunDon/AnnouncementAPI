package co.tide.announcementapi.domain.analyzer;

import co.tide.announcementapi.domain.entity.Announcement;

public interface AnnouncementAnalyzer {

    void analyze(
        Announcement announcement
    );
}
