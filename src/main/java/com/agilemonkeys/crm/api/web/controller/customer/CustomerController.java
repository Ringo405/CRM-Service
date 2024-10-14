package com.agilemonkeys.crm.api.web.controller.customer;

import com.agilemonkeys.crm.api.application.dto.customer.UpdateCustomerUrlImageResponse;
import com.agilemonkeys.crm.api.application.dto.customer.create.CreateCustomerCommand;
import com.agilemonkeys.crm.api.application.dto.customer.create.CreateCustomerResponse;
import com.agilemonkeys.crm.api.application.dto.customer.query.CustomerQuery;
import com.agilemonkeys.crm.api.application.dto.customer.query.CustomerQueryResponse;
import com.agilemonkeys.crm.api.application.dto.customer.query.CustomersQueryResponse;
import com.agilemonkeys.crm.api.application.dto.customer.update.UpdateCustomerCommand;
import com.agilemonkeys.crm.api.application.dto.customer.update.UpdateCustomerResponse;
import com.agilemonkeys.crm.api.application.service.CustomerService;
import com.agilemonkeys.crm.api.infrastructure.integration.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final ImageService imageService;

    public CustomerController(CustomerService customerService, ImageService imageService) {
        this.customerService = customerService;
        this.imageService = imageService;
    }

    /**
     * Endpoint to retrieve all customers.
     *
     * This endpoint allows users to retrieve a list of all customers stored in the database.
     * Access to this endpoint is restricted to users with the 'USER' or 'ADMIN' role.
     *
     * @return ResponseEntity containing a list of customers.
     */
    @Operation(summary = "Retrieve all customers",
            description = "Allows users to retrieve a list of all customers stored in the database.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Customers retrieved successfully",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "403", description = "Forbidden - User access required",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/vnd.api.v1+json"))
            })
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CustomersQueryResponse> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    /**
     * Endpoint to retrieve customer information by ID.
     *
     * This endpoint allows users to get detailed information about a specific customer
     * identified by their unique ID. Access to this endpoint is restricted to users
     * with the 'USER' or 'ADMIN' role.
     *
     * @param id The unique identifier of the customer. This is a required parameter.
     * @return ResponseEntity containing the details of the specified customer.
     */
    @Operation(summary = "Retrieve customer by ID",
            description = "Allows users to get detailed information about a specific customer identified by their unique ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Customer retrieved successfully",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "404", description = "Customer not found",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "403", description = "Forbidden - User access required",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/vnd.api.v1+json"))
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CustomerQueryResponse> getCustomerById(@PathVariable Long id) {
        CustomerQuery customerQuery = CustomerQuery.builder().id(id).build();
        return ResponseEntity.ok(customerService.getCustomerById(customerQuery));
    }

    /**
     * Endpoint to create a new customer.
     *
     * This endpoint allows users to create a new customer in the database.
     * The request body must contain the necessary information to create the customer.
     * Access to this endpoint is restricted to users with the 'USER' or 'ADMIN' role.
     *
     * @param command The command object containing the information for the new customer. This is mandatory.
     * @return ResponseEntity containing the created customer's information.
     */
    @Operation(summary = "Create a new customer",
            description = "Allows users to create a new customer in the database.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Customer created successfully",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "400", description = "Invalid input data",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "403", description = "Forbidden - User access required",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/vnd.api.v1+json"))
            })
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CreateCustomerResponse> createCustomer(@RequestBody CreateCustomerCommand command) {
        return ResponseEntity.ok(customerService.createCustomer(command));
    }

    /**
     * Endpoint to update an existing customer's information.
     *
     * This endpoint allows users to update the information of an existing customer
     * identified by their unique ID. The request body must contain the updated
     * customer information. Access to this endpoint is restricted to users with
     * the 'USER' or 'ADMIN' role.
     *
     * @param id      The unique identifier of the customer to be updated. This parameter is mandatory.
     * @param command The command object containing the updated information for the customer. This is mandatory.
     * @return ResponseEntity containing the details of the updated customer.
     */
    @Operation(summary = "Update an existing customer",
            description = "Allows users to update the information of an existing customer identified by their unique ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Customer updated successfully",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "404", description = "Customer not found",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "400", description = "Invalid input data",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "403", description = "Forbidden - User access required",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/vnd.api.v1+json"))
            })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UpdateCustomerResponse> updateCustomer(@PathVariable Long id, @RequestBody UpdateCustomerCommand command) {
        command.setId(id);
        return ResponseEntity.ok(customerService.updateCustomer(command));
    }

    /**
     * Endpoint to delete an existing customer.
     *
     * This endpoint allows users to delete a customer from the database
     * identified by their unique ID. Access to this endpoint is restricted to
     * users with the 'USER' or 'ADMIN' role.
     *
     * @param id The unique identifier of the customer to be deleted. This parameter is mandatory.
     * @return ResponseEntity with no content to indicate successful deletion.
     */
    @Operation(summary = "Delete an existing customer",
            description = "Allows users to delete a customer from the database identified by their unique ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Customer deleted successfully",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "404", description = "Customer not found",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "403", description = "Forbidden - User access required",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/vnd.api.v1+json"))
            })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint to upload an image for a specific customer.
     *
     * This endpoint allows users to upload an image for a customer identified
     * by their unique ID. The image will be uploaded to a designated storage
     * location, and the customer's photo URL will be updated. Access to this
     * endpoint is restricted to users with the 'USER' or 'ADMIN' role.
     *
     * @param id   The unique identifier of the customer for whom the image is uploaded. This parameter is mandatory.
     * @param file The image file to be uploaded. This is a required parameter.
     * @return ResponseEntity containing the URL of the uploaded image or an error message.
     */
    @Operation(summary = "Upload an image for a specific customer",
            description = "Allows users to upload an image for a customer identified by their unique ID. Updates the customer's photo URL upon successful upload.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Image uploaded successfully",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "404", description = "Customer not found",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "400", description = "Invalid input data",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "403", description = "Forbidden - User access required",
                            content = @Content(mediaType = "application/vnd.api.v1+json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/vnd.api.v1+json"))
            })
    @PostMapping("/{id}/upload-image")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<UpdateCustomerUrlImageResponse> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = imageService.uploadImage(file);
            customerService.updateCustomerPhotoUrl(id, imageUrl);
            return ResponseEntity.ok(new UpdateCustomerUrlImageResponse(imageUrl));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UpdateCustomerUrlImageResponse("Error uploading image: " + e.getMessage()));
        }
    }
}
