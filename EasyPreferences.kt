class LivePersistedProperty<T>(var prefName:String, var default:T):MutableLiveData<T>()
{
    val property = PersistedProperty<T>(prefName,default)
    init{
        setValue(property.value!!)
    }

    override fun setValue(value: T) {
        super.setValue(value)
        this.property.value = value
    }
}

class PersistedProperty<T>(var prefName:String, var default: T)
{
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value!!
    }
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {this.value=value}

    /*
    fun getFresh():T
    {
        value = getPreference(prefName, default)
        return value!!
    }*/

    var value:T? = null
        get(){
            if(field==null)
                field=getPreference(prefName, default)
            return field
        }
        set(value){
            field=value
            savePreference(prefName, value)
        }

}



@Suppress("UNCHECKED_CAST")
fun<T> getPreference(name:String, default:T,prefFile: String? = null):T
{
    val sharedPreferences = App.instance.getSharedPreferences(prefFile?:App.instance.packageName+ "_preferences",Context.MODE_PRIVATE)
   return when(default)
    {
        is String->sharedPreferences.getString(name, default) as T
        is Int->sharedPreferences.getInt(name, default) as T
        is Long->sharedPreferences.getLong(name, default) as T
        is Float->sharedPreferences.getFloat(name, default) as T
        is Boolean->sharedPreferences.getBoolean(name, default) as T
        is MutableSet<*>->sharedPreferences.getStringSet(name, default as MutableSet<String>) as T
        else -> throw(Exception("Not supported type"))
    }
}

fun<T> savePreference(name:String, value:T,prefFile: String? = null)
{

    val sharedPreferences = App.instance.getSharedPreferences(prefFile?:App.instance.packageName+ "_preferences",Context.MODE_PRIVATE)
    when(value)
    {
        is String->sharedPreferences.edit().putString(name, value).apply()
        is Int->sharedPreferences.edit().putInt(name, value).apply()
        is Long->sharedPreferences.edit().putLong(name, value).apply()
        is Float->sharedPreferences.edit().putFloat(name, value).apply()
        is Boolean->sharedPreferences.edit().putBoolean(name, value).apply()
        is MutableSet<*>->sharedPreferences.edit().putStringSet(name, value as MutableSet<String>).apply()
        else -> throw(Exception("Not suported type"))
    }
}
