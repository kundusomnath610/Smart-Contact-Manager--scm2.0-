console.log("console javascript");

const viewContactModal = document.getElementById("view_contact_modal");

const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses: 'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// Initialize the modal instance with the correct options
const contactModal = new Modal(viewContactModal, options);

function openContactModal() {
    contactModal.show();  // Use show() for View the contact
}

function closeContactModal() {
    contactModal.hide(); // use hide to close the view of contact modal..
}

 async function loadContactData(id) {
    console.log(id);

    try {
        const data = await(
            await fetch(`http://127.0.0.1:8081/api/contacts/${id}`)
        ).json();
        console.log(data);
        document.querySelector("#contact_name").innerHTML = data.name;
        document.querySelector("#contact_email").innerHTML = data.email;
        openContactModal();
    } catch (error) {
        console.log("Error :" + error);
    }
}