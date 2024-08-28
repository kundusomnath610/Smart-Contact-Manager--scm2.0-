console.log("console javascript");
const baseUrl = "http://127.0.0.1:8081";
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

// For load data and view the contact Details
 async function loadContactData(id) {
    console.log(id);

    try {
        const data = await(
            await fetch(`${baseUrl}/api/contacts/${id}`)
        ).json();
        console.log(data);
        document.querySelector("#contact_name").innerHTML = data.name;
        document.querySelector("#contact_email").innerHTML = data.email;
        document.querySelector("#contact_phone").innerHTML = data.phoneNumber;
        document.querySelector("#contact_address").innerHTML = data.address;
        document.querySelector("#contact_about").innerHTML = data.description;
        document.querySelector("#contact_image").src = data.picture; 
        
        const contactFavorite = document.querySelector("#contact_favorite"); 
        if(data.favorite) {
            contactFavorite.innerHTML = 
            "<i class='fas fa-star text-yellow-400'></i> <i class='fas fa-star text-yellow-400'></i> <i class='fas fa-star text-yellow-400'></i> <i class='fas fa-star text-yellow-400'></i> <i class='fas fa-star text-yellow-400'> </i>";
    } else {
        contactFavorite.innerHTML = "Not Favorite";
        }
 
        document.querySelector("#contact_website").href = data.websiteLink;
        document.querySelector("#contact_website").innerHTML = data.websiteLink;
        document.querySelector("#contact_linkedIn").href = data.linkedinLink;
        document.querySelector("#contact_linkedIn").innerHTML = data.linkedinLink;
        openContactModal();
    } catch (error) {
        console.log("Error :" + error);
    }
}

// delete Contact

async function deleteContact(id) {
    Swal.fire({
        title: "Are you sure to Delete this Contact?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!",
        cancelButtonText: "Cancel"
    }).then((result) => {
        if (result.isConfirmed) {
            // If confirmed, proceed with the deletion
            const url = `${baseUrl}/user/contacts/delete/` + id;
            window.location.replace(url);
        } else {
            // If canceled, show a message or simply do nothing
            Swal.fire({
                title: "Cancelled",
                text: "Your contact is safe.",
                icon: "error",
                confirmButtonColor: "#3085d6"
            });
        }
    });
}
