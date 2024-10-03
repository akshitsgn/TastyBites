package com.example.ecommerceapp.common.signup


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController){

    var name by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    var confirm by remember {
        mutableStateOf("")
    }
    var selectedRole by remember {
        mutableStateOf("")
    }
    var expanded by remember { mutableStateOf(false) } // Dropdown state

    val roles = listOf("Buyer", "Seller")

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(0.dp)
        .background(Color(0xFFfde6d6)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){

        Text("Sign up", modifier = Modifier.padding(10.dp,0.dp,10.dp,15.dp), fontSize = 29.sp, fontWeight = FontWeight.Medium,)
        Spacer(modifier = Modifier.height(12.dp))
        Text("Just a few quick things to get started!", fontSize = 18.sp,)
        Spacer(modifier = Modifier.height(19.dp))

        OutlinedTextField(
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Toggle password visibility",
                    tint = Color.DarkGray
                )
            },
            value = name,
            onValueChange = { name= it },
            label = { Text("Username") },
            modifier = Modifier.height(60.dp)
                .width(350.dp) // Adjust the width as needed
                .background(Color.White, shape = RoundedCornerShape(32.dp)),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent, // Ensure the background color is applied from Modifier.background
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp) // Ensure the shape is applied
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Toggle password visibility",
                    tint = Color.DarkGray
                )
            },
            value = email,
            onValueChange = { email= it },
            label = { Text("Email") },
            modifier = Modifier
                .height(60.dp)
                .width(350.dp) //  // Adjust the width as needed
                .background(Color.White, shape = RoundedCornerShape(32.dp)),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent, // Ensure the background color is applied from Modifier.background
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp) // Ensure the shape is applied
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Toggle password visibility",
                    tint = Color.DarkGray
                )
            },
            value = password,
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = { password= it },
            isError = password.isNotEmpty() && confirm.isNotEmpty() && password!=confirm,
            label = { Text("Password") },
            modifier = Modifier
                .height(60.dp)
                .width(350.dp) //  // Adjust the width as needed
                .background(Color.White, shape = RoundedCornerShape(22.dp)),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent, // Ensure the background color is applied from Modifier.background
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp) // Ensure the shape is applied
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Toggle password visibility",
                    tint = Color.DarkGray
                )
            },
            value = confirm,
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = {confirm= it },
            label = { Text("Confirm Password") },
            modifier = Modifier
                .height(60.dp)
                .width(350.dp) //  // Adjust the width as needed
                .background(Color.White, shape = RoundedCornerShape(22.dp)),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent, // Ensure the background color is applied from Modifier.background
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(42.dp)// Ensure the shape is applied
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.AccountBox,
                    contentDescription = "Toggle password visibility",
                    tint = Color.DarkGray
                )
            },
            value = selectedRole, // Show the selected role
            onValueChange = {}, // No manual editing allowed
            label = { Text("Select Role") },
            modifier = Modifier
                .height(60.dp)
                .width(350.dp)
                .background(Color.White, shape = RoundedCornerShape(42.dp))
                .clickable { expanded = !expanded }, // Open the dropdown when clicked
            readOnly = true, // Make the field read-only
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp  else Icons.Filled.ArrowDropDown,
                    contentDescription = "Dropdown Arrow",
                    Modifier.clickable { expanded = !expanded }
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp)
        )

        // Dropdown menu content
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(350.dp)
                .background(Color.White)
        ) {
            roles.forEach { role ->
                DropdownMenuItem(
                    text = { Text(role) },
                    onClick = {
                        selectedRole = role // Set selected role
                        expanded = false // Close dropdown
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {



            },
            modifier = Modifier .
            width(300.dp)// //
                .padding(16.dp),
            enabled = name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirm.isNotEmpty() && password==confirm && selectedRole.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0d98ba), // Set button color
                contentColor = Color.White // Set text color
            ),
            shape = RoundedCornerShape(12.dp) // Ensure button has rounded corners
        ) {
            Text("Create account") // Button text
        }
        Row {
            Text("Already have an account?",
                fontSize = 18.sp,)
            Text(" Log In",
                modifier=Modifier.clickable {navController.navigate("signin")  },
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                color = Color(0xFF7851A9)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignInScreen() {
    val navController= rememberNavController()
    SignUpScreen(navController = navController)
}