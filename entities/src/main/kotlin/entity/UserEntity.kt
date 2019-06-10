package entity

data class UserEntity(var id: Long, var familyName: String, var givenName: String) {
    val fullName = "$familyName $givenName"
}