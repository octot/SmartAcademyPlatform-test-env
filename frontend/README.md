1 Authentication 

Login Page :

LoginPage (auth module)
   ↓
AuthContext stores user + permissions
   ↓
navigate("/dashboard")
   ↓
DashboardPage (dashboard module)
   ↓
useAuth() → access user + permissions