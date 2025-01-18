package leegroup.module.sample.gituser.domain.params

data class GetGitUserListParam(
    val since: Long,
    val perPage: Int,
)
