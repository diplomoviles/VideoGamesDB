package com.amaurypm.videogamesdb.extra

fun main() {
    operaNumeros(52,26, { numero1, numero2 ->
        println("La suma de $numero1 + $numero2 es: ${numero1 + numero2}")
    },{ name ->
        println(name)
    })

    operaNumeros(52,26, { numero1, numero2 ->
        println("La multiplicación de $numero1 x $numero2 es: ${numero1 * numero2}")
    },{ name ->
        println("Hola $name")
    })

    //operaNumeros(30,18)

}

fun operaNumeros(num1: Int, num2: Int, operacion: (Int, Int) -> Unit, lambda2: (String) -> Unit){
    //println("La suma de $num1 + $num2 es ${num1+num2}")
    operacion(num1, num2)
    lambda2("Amaury")
}