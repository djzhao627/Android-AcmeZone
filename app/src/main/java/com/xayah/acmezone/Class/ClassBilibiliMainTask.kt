package com.xayah.acmezone.Class

class ClassBilibiliMainTask(
    l_title: String,
    l_progress: String,
    l_reward: String,
    l_state: String
) {
    var title: String = l_title
    var subtitle: String
    var progress: String
    var reward: String
    var state: String

    init {
        progress = l_progress
        reward = l_reward
        state = l_state
        subtitle = "进度"
    }

}