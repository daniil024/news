package com.example.stocks.stocks_main_screen.data.network.models

import com.example.stocks.R

enum class NewsCategory(val serverValue: String, val displayResId: Int) {

    HOME("home", R.string.top_stories_category_home),
    OPINION("opinion", R.string.top_stories_category_opinion),
    WORLD("world", R.string.top_stories_category_world),
    NATIONAL("national", R.string.top_stories_category_national),
    POLITICS("politics", R.string.top_stories_category_politics),
    UPSHOT("upshot", R.string.top_stories_category_upshot),
    BUSINESS("business", R.string.top_stories_category_business),
    TECHNOLOGY("technology", R.string.top_stories_category_technology),
    SCIENCE("science", R.string.top_stories_category_science),
    HEALTH("health", R.string.top_stories_category_health),
    ARTS("arts", R.string.top_stories_category_arts),
    BOOKS("books", R.string.top_stories_category_books),
    MOVIES("movies", R.string.top_stories_category_movies),
    THEATER("theater", R.string.top_stories_category_theater),
    SUNDAY_REVIEW("sundayreview", R.string.top_stories_category_sundayreview),
    FASHION("fashion", R.string.top_stories_category_fashion),
    T_MAGAZINE("tmagazine", R.string.top_stories_category_tmagazine),
    FOOD("food", R.string.top_stories_category_food),
    TRAVEL("travel", R.string.top_stories_category_travel),
    MAGAZINE("magazine", R.string.top_stories_category_magazine),
    REAL_ESTATE("realestate", R.string.top_stories_category_realestate),
    AUTOMOBILES("automobiles", R.string.top_stories_category_automobiles),
    OBITUARIES("obituaries", R.string.top_stories_category_obituaries),
    INSIDER("insider", R.string.top_stories_category_insider);
}