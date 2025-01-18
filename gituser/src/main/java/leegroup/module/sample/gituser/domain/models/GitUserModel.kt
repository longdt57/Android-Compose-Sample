package leegroup.module.sample.gituser.domain.models

data class GitUserModel(
    val id: Long,
    val login: String,
    val avatarUrl: String?,
    val htmlUrl: String?,
)
