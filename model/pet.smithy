namespace example.petstore

/// A pet 
resource Pet {
    identifiers: { 
        petId: PetId
    },
    read: GetPetById,
}

/// Get A pet by that pet's ID
@readonly
@http(
    method: "GET", 
    uri: "/pets/{petId}"
)
operation GetPetById {
    input: GetPetByIdInput,
    output: GetPetByIdOutput,
    errors: [NoSuchPet]
}

structure GetPetByIdInput {
    @required
    @httpLabel
    petId: PetId
}

structure GetPetByIdOutput {
    @required
    id: Integer,

    @required
    name: String,
}

// Unable to find champion with specified name
@error("client")
structure NoSuchPet {
    @required
    championName: PetId
}

// Name of a League of Legends champion 
@pattern("^[A-Za-z0-9 ]+$")
@length(min: 3, max: 20)
string PetId

