//guardar jueguillos
const API = 'http://localhost:8080/api/videojuegos/';

const form = document.getElementById('formularioVideojuego');
const lista = document.getElementById('lista');


//Declarar cada id del HTML y almacenarlo en una variable

const searchBtn = document.getElementById('searchBtn');
const searchInput = document.getElementById('searchInput');
const loader = document.getElementById('loader');
const results = document.getElementById('results');

const API_KEY = '5c9b0cea670f41499bf38f1eaded59cf';

searchBtn.addEventListener('click', ()=>{
  const query = searchInput.value.trim();
  if(query!==''){
    buscarJuegos(query);
  }
});

function buscarJuegos(nombreJuego){
  //mostrar Loader
  loader.classList.remove('hidden');
  results.innerHTML= '';
  //consulta a la API
  const url = `https://api.rawg.io/api/games?key=${API_KEY}&search=${encodeURIComponent(nombreJuego)}`;

  //Fetch permita hacer consultas http GET
  //verifica si la respuesta es correcta y conviete JSON
  fetch(url)
  .then(response=>{
    if(!response.ok) throw new Error('No se pudo conectar a la API');
    return response.json();
  })

  //exito
  .then(data =>{
    mostrarResultados(data.results);
  })

  //validando si algo salio mal

  .catch(error=>{
    results.innerHTML = `<p class="text-red-600">Error: ${error.message}</p>`;
  })

  .finally(()=>{
    loader.classList.add('hidden');
  });
};



function mostrarResultados(juegos){
  if(juegos.length===0){
    results.innerHTML = `<p class="text-red-600">No se encontraron juegos</p>`;
    return
  }

  juegos.forEach(juego=>{
    const div = document.createElement('div');
    div.className ='bg-white rounded shadow p-4';
    div.innerHTML = `
    <img src="${juego.background_image}" alt="${juego.name}" class="w-full h-48 object-cover rounded mb-2">
    <h2 class="text-lg text-gray-600">⭐⭐Rating: ${juego.rating}</h2>
    <p class="text-sm text-gray-600">Lanzamiento: ${juego.relased || 'No hay fecha'}</p>`;
    results.appendChild(div);

  });

};

///SCRIPT GRAFICOS

//Destruir un gráfico si es que existe para evitar superposición
        if(window.myChart){
            window.myChart.destroy();
        }

// MI PRIMER PRIMER GRAFICO DE BARRAS
const ctx = document.getElementById('graficoCategorias').getContext('2d');
window.myChart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: ['Aventura', 'Rol', 'Acción'],
        datasets: [{
            label: 'Stock disponible',
            data: [25, 18, 30],
            backgroundColor: ['#60A5FA', '#A78BFA', '#F87171'],
            borderRadius: 5
        }]
    },
    options: {
        responsive: true,
        plugins: {
            legend: { display: false },
            tooltip: {
                callbacks: {
                    label: context => `${context.parsed.y} juegos`
                }
            }
        },
        scales: {
            y: { beginAtZero: true, title: { display: true, text: 'Cantidad en stock' } },
            x: { title: { display: true, text: 'Categoría' } }
        }
    }
});

//MI SEGUNDO GRAFICO DE PASTEL.
const ctxMensual1 = document.getElementById('graficoStockMensual').getContext('2d');
window.myChart = new Chart(ctxMensual1, {
  type: 'pie',
  data: {
    labels: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo'],
    datasets: [{
      data: [40, 30, 25, 20, 35],
      backgroundColor: ['#60A5FA', '#FBBF24', '#34D399', '#F472B6', '#A78BFA'],
      borderColor: '#FFFFFF',
      borderWidth: 2
    }]
  },
  options: {
    responsive: true,
    plugins: {
      legend: {
        position: 'bottom',
        labels: {
          color: '#374151',
          font: {
            size: 14,
            weight: '500'
          }
        }
      },
      tooltip: {
        callbacks: {
          label: context => `${context.label}: ${context.parsed} juegos`
        }
      }
    }
  }
});





//AGREGUE TOASTIFY

    form.addEventListener('submit', async (e) =>{
        e.preventDefault();
        const vj = {
            titulo: form.titulo.value,
            genero: form.genero.value,
            plataforma: form.plataforma.value,
            precio: parseFloat(form.precio.value),
            stock: parseInt(form.stock.value)
        };
        try {
    const res = await fetch(API, {
      method: 'POST',
      headers: { 'Content-type': 'application/json' },
      body: JSON.stringify(vj)
    });
    if (!res.ok) throw new Error('Error al agregar el juego');
    Toastify({
      text: "¡Juego agregado con éxito!",
      duration: 3000,
      gravity: "top",
      position: "right",
      backgroundColor: "linear-gradient(to right, #4caf50, #388e3c)",
    }).showToast();
    form.reset();
    cargar();
  } catch (error) {
    Toastify({
      text: "Error: " + error.message,
      duration: 4000,
      gravity: "top",
      position: "right",
      backgroundColor: "linear-gradient(to right, #f44336, #d32f2f)",
    }).showToast();
  }
});



    //Función de cargargg
    async function cargar(){
        const res = await fetch(API);
        const data = await res.json();
    //alt GR + }}
        lista.innerHTML =`
            <table class="table-auto w-full text-left bg-white shadow-md rounded overflow-hidden">
                <thead class="bg-blue-600 text-white">
                    <tr>
                        <th className="px-4 py-2">Título</th>
                        <th className="px-4 py-2">Género</th>
                        <th className="px-4 py-2">Plataforma</th>
                        <th className="px-4 py-2">Precio</th>
                        <th className="px-4 py-2">Stock</th>
                    </tr>
                </thead>
                <tbody id="tablaBody" class="divide-y divide-gray-200"></tbody>
            </table>`;

        const tablaBody = document.getElementById('tablaBody');
        const labels = [];
        const datos = [];

        //FOR=  podemos detenerlo ///ForEach= no se detiene
        data.forEach(v =>{
            tablaBody.innerHTML +=`
            <tr>
                <td class="px-4 py-2">${v.titulo}</td>
                <td class="px-4 py-2">${v.genero}</td>
                <td class="px-4 py-2">${v.plataforma}</td>
                <td class="px-4 py-2">${v.precio}</td>
                <td class="px-4 py-2">${v.stock}</td>
            </tr>
            `;
            labels.push(v.titulo);
            datos.push(v.stock);
        });

   

        //Gráfico
        window.myChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Stock por Videojuego',
                    data: datos,
                    backgroundColor: 'rgba(59, 130, 256, 0.6)',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: { beginAtZero: true}
                }
            }
        });
    }
cargar();