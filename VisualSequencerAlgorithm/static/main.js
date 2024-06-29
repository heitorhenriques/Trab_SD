let currentSequencer = 0;
let iteration = 1;

const circles = [
	{ id: "circle1", label: "P1" },
	{ id: "circle2", label: "P2" },
	{ id: "circle3", label: "P3" },
	{ id: "circle4", label: "P4" },
	{ id: "circle5", label: "P5" },
	{ id: "circle6", label: "P6" },
];

function sendMessages(event) {
	event.preventDefault();

	// Disable the send button while processing
	document.querySelector('button[type="submit"]').disabled = true;

	const clients = ["client1", "client2", "client3"];
	let messages = [];

	clients.forEach((client) => {
		const clientCheckbox = document.getElementById(`${client}Checkbox`);
		if (clientCheckbox.checked) {
			messages.push(document.getElementById(`${client}Message`).value);
		} else {
			messages.push("-");
		}
	});

	const reorderedMessages = messages.filter((msg) => msg !== "-").reverse();
	updateClientTable(clients, messages);
	processMessage(reorderedMessages);
}

function processMessage(messages) {
	// Display the message above the current sequencer
	const currentSequencercircle = document.getElementById(
		circles[currentSequencer].id
	);
	displayMessage(currentSequencercircle, `Reordered: ${messages.join(", ")}`);

	// Update the sending table for processes
	updateProcessTable(messages);

	// Simulate recipients processing the message
	const recipientTimes = [
		Math.random() * 4000 + 3000,
		Math.random() * 4000 + 3000,
		Math.random() * 4000 + 3000,
	];
	const recipients = ["recipient1", "recipient2", "recipient3"];

	let recipientFinishedFirst = false;

	recipients.forEach((recipient, index) => {
		setTimeout(() => {
			displayMessage(
				document.getElementById(recipient),
				`I received message ${messages.join(", ")}`
			);

			setTimeout(() => {
				if (!recipientFinishedFirst) {
					recipientFinishedFirst = true;
					displayMessage(
						document.getElementById(recipient),
						"I finished dealing, telling other recipients and sending back an OK"
					);
					recipients
						.filter((r) => r !== recipient)
						.forEach((r) => {
							displayMessage(
								document.getElementById(r),
								"Another recipient finished first"
							);
						});
					updateRecipientTable(recipient);
					updateReceivingTable(messages, recipient);
				}
			}, recipientTimes[index]);
		}, recipientTimes[index]);
	});

	setTimeout(() => {
		currentSequencercircle.querySelector(".message").remove();
		currentSequencer = (currentSequencer + 1) % circles.length;
		moveSequencer();
		clearMessage();
		document.querySelector('button[type="submit"]').disabled = false;
	}, 12000); // 6 seconds for recipients + 6 seconds for OK
}

function displayMessage(circle, message) {
	let messageElement = circle.querySelector(".message");
	if (!messageElement) {
		messageElement = document.createElement("div");
		messageElement.className = "message";
		circle.appendChild(messageElement);
	}
	messageElement.innerText = message;
}

function moveSequencer() {
	circles.forEach((circle, index) => {
		const circleElement = document.getElementById(circle.id);
		if (index === currentSequencer) {
			circleElement.classList.add("sequencer");
		} else {
			circleElement.classList.remove("sequencer");
		}
	});
}

function updateClientTable(clients, messages) {
	const table = document
		.getElementById("sendingTable")
		.getElementsByTagName("tbody")[0];

	// Create new row for current iteration
	const row = table.insertRow();
	const iterationCell = row.insertCell();
	iterationCell.innerText = iteration;

	// Update client cells
	clients.forEach((client, index) => {
		const cell = row.insertCell();
		const clientCheckbox = document.getElementById(`${client}Checkbox`);
		if (clientCheckbox.checked) {
			cell.innerText = messages[index];
		} else {
			cell.innerText = "-";
		}
	});
}

function updateProcessTable(messages) {
	const table = document
		.getElementById("sendingTable")
		.getElementsByTagName("tbody")[0];
	const row = table.rows[table.rows.length - 1];

	// Update process cell
	for (let i = 0; i < circles.length; i++) {
		const cell = row.insertCell();
		if (i === currentSequencer) {
			cell.innerText = messages.join(", ");
		} else {
			cell.innerText = "-";
		}
	}
}

function updateRecipientTable(finishedRecipient) {
	const table = document
		.getElementById("sendingTable")
		.getElementsByTagName("tbody")[0];
	const row = table.rows[table.rows.length - 1];

	// Update recipient cells
	const recipients = ["recipient1", "recipient2", "recipient3"];
	recipients.forEach((recipient) => {
		const cell = row.insertCell();
		if (recipient === finishedRecipient) {
			cell.innerText = "OK";
		} else {
			cell.innerText = "-";
		}
	});
}

function updateReceivingTable(messages) {
	const table = document
		.getElementById("receivingTable")
		.getElementsByTagName("tbody")[0];

	// Create new row for current iteration
	const row = table.insertRow();
	const iterationCell = row.insertCell();
	iterationCell.innerText = iteration;

	// Update process cell
	for (let i = 0; i < circles.length; i++) {
		const cell = row.insertCell();
		if (i === currentSequencer) {
			cell.innerText = messages.reverse().join(", ") + ", OK";
		} else {
			cell.innerText = "-";
		}
	}

	let newMessage = messages.reverse();

	for (let i = 0; i < 3; i++) {
		const cell = row.insertCell();
		cell.innerText = newMessage.join(", ");
	}
	iteration++;
}

function clearMessage(process) {
	const recipients = ["recipient1", "recipient2", "recipient3"];
	recipients.forEach((recipientId) => {
		const recipient = document.getElementById(recipientId);
		const messageDiv = recipient.querySelector(".message");
		if (messageDiv) {
			recipient.removeChild(messageDiv);
		}
	});
}
moveSequencer();
