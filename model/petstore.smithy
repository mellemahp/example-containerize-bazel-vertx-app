namespace example.petstore

use example.petstore#Pet
use aws.protocols#restJson1


/// Provides recommendations for runepages and item sets for a champion
@restJson1
@title("PetStore")
service PetStoreService {
    version: "2006-03-01", 
    resources: [Pet],
}