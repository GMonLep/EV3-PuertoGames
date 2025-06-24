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

    //Función de cargar
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