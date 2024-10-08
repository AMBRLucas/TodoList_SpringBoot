const url = "http://localhost:8080/task/user/1";

const tasklist = document.querySelector(".tasklist");

const tasks = async () => {
    try{

        const response = await fetch(url);
        if(!response.ok){
            throw Error("nao foi possivel obter informações");
        }

        const tasksList = await response.json();
        console.log(tasksList);

        tasksList.map((task, index) => {
            const taskitem = document.createElement("div");
            taskitem.classList.add("taskitem");
            tasklist.setAttribute("key", task.id);
            const id = document.createElement("div");
            id.innerHTML = `${task.id}`;
            id.classList.add("id");
            const desc = document.createElement("div");
            desc.innerHTML = `${task.description}`;
            desc.classList.add("desc");
            const user = document.createElement("div");
            user.innerHTML = `${task.user.username}`;
            user.classList.add("user");
            const del = document.createElement("button");
            del.innerHTML = "Delete task";
            del.setAttribute("key", task.id);
            del.classList.add("delete");

            tasklist.appendChild(taskitem);
            taskitem.appendChild(id);
            taskitem.appendChild(desc);
            taskitem.appendChild(user);
            taskitem.appendChild(del);

            del.addEventListener("click", async(event)=>{
                const keyValue = event.target.getAttribute("key");
                try{
                    const response = await fetch(`http://localhost:8080/task/${keyValue}`, {method: "DELETE", headers: {'Content-Type': 'application/json'}});
                    if(!response.ok){
                        throw new Error('Network response was not ok ' + response.statusText);
                    }
                    const result = await response.json();
                    console.log('Item deletado:', result);
                }catch(error){

                }
            })
        })

    }catch(error){

    }
}

tasks();
