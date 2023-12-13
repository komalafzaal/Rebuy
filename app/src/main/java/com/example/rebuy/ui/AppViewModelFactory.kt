//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.example.rebuy.Model.Repository.AuthRepository
//import com.example.rebuy.ViewModels.LoginViewModel
//
//class AppViewModelFactory(private val repository: AuthRepository) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
//            return LoginViewModel(repository) as T
//        }
//
////        if (modelClass.isAssignableFrom(c::class.java)) {
////            return UserViewModel(
////
////                )
////            ) as T
////        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
