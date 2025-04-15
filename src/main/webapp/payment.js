document.addEventListener('DOMContentLoaded', function() {
    const paymentForm = document.getElementById('paymentForm');
    const paymentMethodRadios = document.querySelectorAll('input[name="paymentMethod"]');
    const cardFields = document.getElementById('cardFields');
    const netbankingFields = document.getElementById('netbankingFields');
    const upiFields = document.getElementById('upiFields');

    // Handle payment method selection
    paymentMethodRadios.forEach(radio => {
        radio.addEventListener('change', function() {
            // Hide all payment fields first
            cardFields.style.display = 'none';
            netbankingFields.style.display = 'none';
            upiFields.style.display = 'none';

            // Show relevant fields based on selection
            if (this.value === 'credit' || this.value === 'debit') {
                cardFields.style.display = 'block';
            } else if (this.value === 'netbanking') {
                netbankingFields.style.display = 'block';
            } else if (this.value === 'upi') {
                upiFields.style.display = 'block';
            }
        });
    });

    // Handle form submission
    paymentForm.addEventListener('submit', function(e) {
        e.preventDefault();
        
        const selectedMethod = document.querySelector('input[name="paymentMethod"]:checked').value;
        let isValid = true;
        let errorMessage = '';

        // Validate based on selected payment method
        if (selectedMethod === 'credit' || selectedMethod === 'debit') {
            const cardNumber = document.getElementById('cardNumber').value;
            const cardName = document.getElementById('cardName').value;
            const expiry = document.getElementById('expiry').value;
            const cvv = document.getElementById('cvv').value;

            if (!cardNumber || !cardName || !expiry || !cvv) {
                isValid = false;
                errorMessage = 'Please fill in all card details';
            }
        } else if (selectedMethod === 'netbanking') {
            const bank = document.getElementById('bank').value;
            if (!bank) {
                isValid = false;
                errorMessage = 'Please select a bank';
            }
        } else if (selectedMethod === 'upi') {
            const upiId = document.getElementById('upiId').value;
            if (!upiId) {
                isValid = false;
                errorMessage = 'Please enter your UPI ID';
            }
        }

        if (isValid) {
            // Show loading state
            const submitButton = paymentForm.querySelector('button[type="submit"]');
            const originalText = submitButton.innerHTML;
            submitButton.innerHTML = '<span class="loading">Processing Payment...</span>';
            submitButton.disabled = true;

            // Simulate payment processing
            setTimeout(() => {
                // Show success message
                const successMessage = document.createElement('div');
                successMessage.className = 'success';
                successMessage.textContent = 'Payment successful! Your ticket has been booked.';
                paymentForm.appendChild(successMessage);

                // Reset form
                submitButton.innerHTML = originalText;
                submitButton.disabled = false;
                
                // Redirect to confirmation page after 2 seconds
                setTimeout(() => {
                    window.location.href = 'confirmation.html';
                }, 2000);
            }, 2000);
        } else {
            // Show error message
            const errorElement = document.createElement('div');
            errorElement.className = 'error';
            errorElement.textContent = errorMessage;
            paymentForm.insertBefore(errorElement, paymentForm.firstChild);
        }
    });

    // Format card number input
    const cardNumberInput = document.getElementById('cardNumber');
    cardNumberInput.addEventListener('input', function(e) {
        let value = e.target.value.replace(/\D/g, '');
        value = value.replace(/(\d{4})/g, '$1 ').trim();
        e.target.value = value;
    });

    // Format expiry date input
    const expiryInput = document.getElementById('expiry');
    expiryInput.addEventListener('input', function(e) {
        let value = e.target.value.replace(/\D/g, '');
        if (value.length >= 2) {
            value = value.slice(0, 2) + '/' + value.slice(2);
        }
        e.target.value = value;
    });

    // Format CVV input
    const cvvInput = document.getElementById('cvv');
    cvvInput.addEventListener('input', function(e) {
        e.target.value = e.target.value.replace(/\D/g, '');
    });
});

// Handle UPI app selection
function selectUpiApp(app) {
    const upiButtons = document.querySelectorAll('.upi-app-btn');
    upiButtons.forEach(button => {
        button.classList.remove('selected');
    });
    event.target.closest('.upi-app-btn').classList.add('selected');
} 