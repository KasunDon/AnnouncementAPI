package co.tide.announcementapi.controller;

import co.tide.announcementapi.domain.analyzer.AnnouncementAnalyzer;
import co.tide.announcementapi.domain.entity.Announcement;
import co.tide.announcementapi.domain.entity.AnnouncementFromParameterFactory;
import co.tide.announcementapi.domain.entity.Rating;
import co.tide.announcementapi.domain.persistence.AnnouncementRatingFetcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static co.tide.announcementapi.library.JsonOutputWrapper.wrap;

public class ApplicationController {

    private final AnnouncementAnalyzer announcementAnalyzer;
    private final AnnouncementFromParameterFactory announcementFromParameterFactory;
    private final AnnouncementRatingFetcher announcementRatingFetcher;

    public ApplicationController(
        AnnouncementAnalyzer announcementAnalyzer,
        AnnouncementFromParameterFactory announcementFromParameterFactory,
        AnnouncementRatingFetcher announcementRatingFetcher
    ) {
        this.announcementAnalyzer = announcementAnalyzer;
        this.announcementFromParameterFactory = announcementFromParameterFactory;
        this.announcementRatingFetcher = announcementRatingFetcher;
    }

    public Map<String, Object> performRateAction(
        Map<String, String> parameters
    ) {

        /**
         * TODO - must abstract into validators and parameter extractors instead using factory to handle basic validations for http requests
         */
        Announcement announcement = announcementFromParameterFactory.create(parameters);

        announcementAnalyzer.analyze(announcement);

        return wrap("success", true);
    }

    public Map<String, String> fetchRateAction(
        Map<String, String> parameters
    ) {
        /**
         * TODO - must abstract into validators and parameter extractors instead using factory to handle basic validations for http requests
         */
        Announcement announcement = announcementFromParameterFactory.create(parameters);

        Optional<Map<Rating, Integer>> ratingCountMap =
            announcementRatingFetcher.fetch(announcement.getId());

        if (!ratingCountMap
            .isPresent()) {
            return wrap("message", "No like/dislike counters found for announcementId - " + announcement.getId());
        }

        return covertToStringMap(ratingCountMap.get());
    }


    private Map<String, String> covertToStringMap(
        Map<Rating, Integer> counterMap
    ) {
        Map<String, String> outputMap = new HashMap<>();

        for (Rating key : counterMap.keySet()) {
            outputMap.put(key.toString(), "" + counterMap.get(key));
        }

        return outputMap;
    }
}
