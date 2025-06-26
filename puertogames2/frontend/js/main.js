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
    if(!response.ok) throw new Error('No se pudo conectar a la API👀');
    return response.json();
  })

  //exito
  .then(data =>{
    mostrarResultados(data.results);
  })

  //validando si algo salio mal

  .catch(error=>{
    results.innerHTML = `<p class="text-red-600">✖️Error: ${error.message}</p>`;
  })

  .finally(()=>{
    loader.classList.add('hidden');
  });
};



function mostrarResultados(juegos){
  if(juegos.length===0){
    results.innerHTML = `<p class="text-red-600">✖️ No se encontraron juegos</p>`;
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

///SCRIPT GRAFICOS Y AÑADIR JUEGOS
const API = 'http://localhost:8080/api/videojuegos';

const form = document.getElementById('form');
const lista = document.getElementById('lista');
const ctx = document.getElementById('grafico').getContext('2d');

    form.addEventListener('submit', async (e) =>{
        e.preventDefault();

        const vj = {
            titulo: form.titulo.value,
            genero: form.genero.value,
            plataforma: form.plataforma.value,
            precio: parseFloat(form.precio.value),
            stock: parseInt(form.stock.value)

        };

        await fetch(API, {
            method: 'POST',
            headers: { 'Content-type': 'application/json'},
            body: JSON.stringify(vj)
        });
        form.reset();
        cargar();
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

        //Destruir un gráfico si es que existe para evitar superposición
        if(window.myChart){
            window.myChart.destroy();
        }

        //Gráfico
        window.myChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Stock por Videojuego',
                    data: datos,
                    backgroundColor: 'rgba(59, 130, 256, 0,6)',
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