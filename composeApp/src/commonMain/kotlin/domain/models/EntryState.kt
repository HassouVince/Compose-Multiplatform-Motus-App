package domain.models
 sealed class EntryState(val entry: String? = null) {
     class Success(entry: String? = null): EntryState(entry) {
         override fun toString(): String {
             return "Success()"
         }
         override fun equals(other: Any?): Boolean {
             if (this === other) return true
             if (other == null || this::class != other::class) return false
             return true
         }
         override fun hashCode(): Int {
             return this::class.hashCode()
         }
     }

     class InvalidLengthEntry: EntryState(){
         override fun toString(): String {
             return "InvalidLengthEntry()"
         }
         override fun equals(other: Any?): Boolean {
             if (this === other) return true
             if (other == null || this::class != other::class) return false
             return true
         }
         override fun hashCode(): Int {
             return this::class.hashCode()
         }
     }

     class UnknownEntry(entry: String): EntryState(entry){
         override fun toString(): String {
             return "UnknownEntry($entry)"
         }
         override fun equals(other: Any?): Boolean {
             if (this === other) return true
             if (other == null || this::class != other::class) return false
             return true
         }
         override fun hashCode(): Int {
             return this::class.hashCode()
         }
     }
     class WrongEntry(entry: String): EntryState(entry){
         override fun toString(): String {
             return "WrongEntry($entry)"
         }
         override fun equals(other: Any?): Boolean {
             if (this === other) return true
             if (other == null || this::class != other::class) return false
             return true
         }
         override fun hashCode(): Int {
             return this::class.hashCode()
         }
     }
 }